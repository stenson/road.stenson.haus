(ns road.geo
  (:require [clojure.string :as string]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.json :as json]
            [clojure.pprint :refer [pprint]]
            [clj-xpath.core :as xp]))

(comment "http://ogre.adc4gis.com/")
(def ^:private roadtrip-map-url (string/trim (slurp "map-id")))

(def ^:private kml (slurp "doc.kml"))

(def ^:private colors->types
  {"DB4436" "Indeterminate"
   "0BA9CC" "Market"
   "4186F0" "Restaurant"
   "9FC3FF" "Restaurant"
   "3F5BA9" "Bar"
   "777777" "Museum"
   "795046" "Museum"
   "F4EB37" "POI"
   "7C3592" "Coffee"
   "A61B4A" "Coffee"
   "62AF44" "Shopping"
   "009D57" "Liquor Store"
   "F4B400" "Hotel"
   "FAD199" "Hotel"
   "F8971B" "Hotel"
   "CDDC39" "Venue"})

(def ^:private types->markers
  {"Indeterminate" ["#EEE"]
   "Restaurant" ["#4186F0" "fast-food"]
   "Bar" ["#3F5BA9" "bar"]})

(defn- placemark->place [p]
  (let [icon-url (xp/$x:text "./styleUrl" p)
        color (second (re-matches #".*-([A-F0-9]{6})(-.*)?" icon-url))]
    {:name (xp/$x:text "./name" p)
     :color color
     :icon (get colors->types color "N/A")
     :coords (map #(Float/parseFloat %)
                  (string/split
                    (xp/$x:text "./Point/coordinates" p)
                    #","))}))

(defn ?assoc
  "Same as assoc, but skip the assoc if v is nil"
  [m & kvs]
  (->> kvs
       (partition 2)
       (filter second)
       (map vec)
       (into m)))

(defn- place->geojson-feature [{:keys [name color icon coords]}]
  (let [[marker-color marker-symbol] (get types->markers icon ["#CCC"])
        props {:title name :marker-size "medium"}]
    {:type "Feature"
     :geometry {:type "Point", :coordinates coords}
     :properties (?assoc props
                         :marker-color marker-color
                         :marker-symbol marker-symbol)}))

(defn to-geojson []
  (->> (xp/$x "//Folder" kml)
       (filter #(= "Restaurants" (xp/$x:text "./name" %)))
       (first)
       (xp/$x "./Placemark")
       (map placemark->place)
       ;(group-by :icon)
       (map place->geojson-feature)
       (assoc {:type "FeatureCollection"} :features)
       (json/write-str)
       (spit "site/assets/js/features.json")
       ))