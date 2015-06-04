(ns road.core
  (:require [tonal.core :as tonal]
            [road.mod :as mod]
            [road.geo :as geo]
            [clojure.data.json :as json]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str]))

(defn- index->points [index]
  (->> (:grouped-articles index)
       (map :articles)
       (flatten)
       (map #(select-keys % [:title :lat :lng :symbol]))))

(defn- points->geojson [points]
  (->> points
       (map (fn [{:keys [title lat lng symbol]}]
              {:type "Feature"
               :geometry {:type "Point"
                          :coordinates [lng lat]}
               :properties (geo/?assoc {:title title
                                        :marker-size "small"
                                        :marker-color "#CD5C5C"}
                                       :marker-symbol symbol)}))
       (assoc {:type "FeatureCollection"} :features)
       (json/write-str)))

(defn- index-mod [index]
  (let [points (index->points index)]
    (-> index
        (assoc :geo-data (points->geojson points))
        (assoc :latest (first points)))))

(defn- config-mod [config]
  (assoc config :index-mod index-mod))

(def app
  (tonal/app-with-mod identity config-mod))

(defn print-site []
  (tonal/print-site false config-mod))