(ns day22
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def raw-data  (-> "sample22.dat"
                   io/resource
                   slurp
                   (str/split #"\n\n")
                   ))
(def p1 (->> (first raw-data)
             (str/split-lines)
             (drop 1)
             (map read-string)
             ))
(def p2 (->> (second raw-data)
             (str/split-lines)
             (drop 1)
             (map read-string)
             ))

(defn play [p1deck p2deck]
  (loop [p1 (first p1deck) p1s (rest p2deck)
         p2 (first p2deck) p2s (rest p2deck)
         turn 0
         ]
    (cond (nil? p1) [p2s turn :two]
          (nil? p2) [p1s turn :one]
          
          )
    ))
