(ns road.geo
  (:require [clojure.string :as string]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.pprint :refer [pprint]]
            [clj-xpath.core :as xp]))

(comment "http://ogre.adc4gis.com/")
(def ^:private roadtrip-map-url (string/trim (slurp "map-id")))

(def ^:private kml (slurp "doc.kml"))

(def ^:private colors->types
  {"DB4436" "Indeterminate"
   "0BA9CC" "Market"
   "4186F0" "Restaurant"
   "3F5BA9" "Bar"
   "777777" "Museum"
   "F4EB37" "POI"
   "7C3592" "Coffee"
   "62AF44" "Shopping"
   "009D57" "Liquor Store"
   "F4B400" "Hotel"
   "FAD199" "Hotel"
   "F8971B" "Hotel"
   "CDDC39" "Venue"})

(defn- icon-url->type [icon-url]
  (let [color (second (re-matches #".*-([A-F0-9]{6})(-.*)?" icon-url))]
    (get colors->types color (or color icon-url))))

(defn- to-geojson []
  (->> (xp/$x "//Folder" kml)
       (filter #(= "Restaurants" (xp/$x:text "./name" %)))
       (first)
       (xp/$x "./Placemark")
       (map (fn [p]
              (let [icon (xp/$x:text "./styleUrl" p)]
                {:name (xp/$x:text "./name" p)
                 :icon (icon-url->type icon)})))))