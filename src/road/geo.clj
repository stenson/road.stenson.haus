(ns road.geo
  (:require [clojure.string :as string]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.json :as json]
            [clojure.pprint :refer [pprint]]
            [clj-xpath.core :as xp]))

(defn ?assoc
  "Same as assoc, but skip the assoc if v is nil"
  [m & kvs]
  (->> kvs
       (partition 2)
       (filter second)
       (map vec)
       (into m)))

(def ^:private kml (slurp "doc.kml"))

(defn- mark
  ([name from-colors]
    (mark name from-colors nil))
  ([name from-colors icon]
   {:name name
    :from-colors (into #{} from-colors)
    :color (str "#" (first from-colors))
    :icon icon}))

(def ^:private markers
  [(mark "Indeterminate" ["DB4436"])
   (mark "Market" ["0BA9CC"])
   (mark "Restaurant" ["4186F0" "9FC3FF"] "fast-food")
   (mark "Bar" ["3F5BA9"] "bar")
   (mark "Museum" ["777777" "795046"] "museum")
   (mark "POI" ["F4EB37"])
   (mark "Coffee" ["7C3592" "A61B4A"])
   (mark "Shopping" ["62AF44" "009D57"])
   (mark "Hotel" ["F4B400" "FAD199" "F8971B"])
   (mark "Venue" ["CDDC39"])])

(defn- placemark->feature [p]
  (let [name (xp/$x:text "./name" p)
        icon-url (xp/$x:text "./styleUrl" p)
        color (second (re-matches #".*-([A-F0-9]{6})(-.*)?" icon-url))
        coords (map #(Float/parseFloat %)
                    (string/split
                      (xp/$x:text "./Point/coordinates" p)
                      #","))
        marker (first (filter #(contains? (:from-colors %) color) markers))]
    {:type "Feature"
     :geometry {:type "Point", :coordinates coords}
     :properties (?assoc {:title name
                          :marker-size "medium"
                          :marker-color (:color marker)}
                         :marker-symbol (:icon marker))}))

(defn to-geojson []
  (->> (xp/$x "//Folder" kml)
       (filter #(= "Restaurants" (xp/$x:text "./name" %)))
       (first)
       (xp/$x "./Placemark")
       (map placemark->feature)
       (assoc {:type "FeatureCollection"} :features)
       (json/write-str)
       (spit "site/assets/js/features.json")
       ))