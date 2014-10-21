(ns clojure-redis-example.core
  (:import [org.joda.time DateTime]
           [java.util Date])
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clj-time.coerce :as time-coerce]
            [clj-time.core :as t]
            [clojure.data.json :as json]))

(def server-connection {:pool {} :spec {:host "127.0.0.1" :port 6379}})
(defmacro wcar* [& body] `(car/wcar server-connection ~@body))

(comment
  "examples of simple functions in Redis"

  (wcar* (car/hmset* "yyy" {:yyy "yyy", :pass "yyyyyy"}))
  (wcar* (car/ping)
    (car/zadd "tag:isvag-tag0" 10002 "{\"min\": 4, \"max\":104}")

    (car/get "foo"))
  )

(defn- DateTime->millis [^DateTime dateTime]
  (->
    dateTime
    (.getMillis)))

(defn decrement-seconds-to-date [^DateTime startDate decrement]
  (.minusSeconds startDate decrement))

(def counter (atom 0))

(def keyCounter (atom 0))


(defn generate-random-value [value]
  (->
    (Math/sin value)
    (* 100)
    int
    (+ (rand-int 10))))

(defn generate-key-value-document [keyValue]
  (let [swapedValue (swap! counter inc)
        value (generate-random-value swapedValue)]
    [keyValue (json/write-str {:min value
                               :max (+ value 100)})]))

(defn generate-dateTime-intervals [decrement]
  (->>
    (t/now)
    (iterate #(decrement-seconds-to-date % decrement))
    (map DateTime->millis)
    (map generate-key-value-document)))

(defn add-entries-in-Redis [[firstEntry secondEntry]]
  (do
    (car/zadd "benchmark" (swap! keyCounter inc) secondEntry)
    (println @keyCounter)))

(defn print-values [[firstEntry secondEntry]]
  (pr (str "first value: " (str @counter) " second value: " secondEntry)))

(defn run-benchmark [num-entries]
  (->>
    (generate-dateTime-intervals 15)
    (map add-entries-in-Redis)
    (take num-entries)
    doall
    wcar*
    time))

(defn -main [& [entries]]
  ;  (run-benchmark 2))
  (let [num-entries (Integer. (or entries (System/getenv "ENTRIES")))]
    (run-benchmark num-entries)))



