(defproject
  road
  "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;[org.clojure/data.xml "0.0.8"]
                 ;[hiccup "1.0.5"]
                 ;[stasis "2.2.2"]
                 ;[environ "0.5.0"]
                 ;[stencil "0.3.4"]
                 ;[ring "1.3.2"]
                 ;[circleci/clj-yaml "0.5.3"]
                 ;[clj-time "0.8.0"]
                 ;[me.raynes/fs "1.4.6"]
                 ;[hieronymus "0.1.0-SNAPSHOT"]
                 [tonal "0.1.0-SNAPSHOT"]
                 ]
  :plugins [[lein-ring "0.9.1"]]
  :ring {:handler road.core/app}
  :profiles {:dev {:env {:config "site/"}}})
