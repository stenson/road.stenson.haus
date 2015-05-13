(ns road.core
  (:require [tonal.core :as tonal]
            [road.mod :as mod]
            [road.geo :as geo]
            [clojure.string :as str]))

(def app
  (tonal/app-with-mod
    (fn [handler]
      (fn [req]
        (let [res (handler req)]
          (if (-> (get-in res [:headers "Content-Type"])
                  (str/split #";")
                  (first)
                  (= "text/html"))
            (update-in res [:body] (partial mod/road-mod req res))
            res))))
    (fn [config]
      ;(assoc config :geojson (geo/to-geojson))
      config)))
