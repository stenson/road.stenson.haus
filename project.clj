(defproject
  road
  "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [hiccup "1.0.5"]
                 [hickory "0.5.4"]
                 [environ "0.5.0"]
                 [com.github.kyleburton/clj-xpath "1.4.4"]
                 [tonal "0.1.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.9.1"]]
  :ring {:handler road.core/app}
  :profiles {:dev {:env {:config "site/"}}}
  :aliases {"print-site" ["run" "-m" "road.core/print-site"]})
