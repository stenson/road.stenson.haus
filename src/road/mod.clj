(ns road.mod
  (:require [hiccup.core :as hiccup]
            [hickory.core :as hickory]
            [hickory.zip :as hzip]
            [clojure.zip :as zip]))

(def ^:private map-headers
  [[:script {:src "https://api.tiles.mapbox.com/mapbox.js/v2.1.9/mapbox.js"}]
   [:link {:href "https://api.tiles.mapbox.com/mapbox.js/v2.1.9/mapbox.css"
           :rel "stylesheet"}]
   [:script {:src "/js/map.js"}]])

(def html-transforms
  {:head
   (fn [_ x] (reduce #(conj %1 %2) x map-headers))
   :div#header-container
   (fn [_ x] (conj x [:div#menu
                        [:a {:href "/about"} "About"]
                        [:span.slash " / "]
                        [:a {:href "/contact"} "Contact"]]))})

(defn mod-html [req transforms z]
  (loop [z z]
    (if (identical? (zip/next z) z)
      (zip/root z)
      (let [[tag attrs & _] (zip/node z)
            id (get attrs :id)
            sel (if id (keyword (str (name tag) "#" id)) tag)
            tf (get transforms sel)]
        (if (and tf (zip/branch? z))
          (-> z
              (zip/edit (partial tf req))
              (zip/next)
              (recur))
          (recur (zip/next z)))))))

(defn road-mod [req _ html-string]
  (comment
    (let [html (hickory/as-hiccup (hickory/parse html-string))]
      (hiccup/html (mod-html req html-transforms (hzip/hiccup-zip html)))))
  html-string)