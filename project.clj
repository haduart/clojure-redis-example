(defproject clojure-redis-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/haduart/clojure-redis-example"
  :license {:name "BSD"
            :url "http://www.opensource.org/licenses/BSD-3-Clause"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.taoensso/carmine "2.7.1"]
                 [clj-time "0.6.0"]
                 [org.clojure/data.json "0.2.4"]]


  :plugins [[lein-midje "3.1.3"]
            [lein-pprint "1.1.1"]
            [lein-ancient "0.5.5"]]

  :repl-options {:welcome (println "Welcome to the magical world of the repl!")
                 :port    4001}

  :min-lein-version "2.0.0"

  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"] [midje "1.6.3"]
                                  [peridot "0.2.2"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0-alpha1"]]}}

  :aliases {"dev" ["do" "test"]}

  :main clojure-redis-example.core
  )

