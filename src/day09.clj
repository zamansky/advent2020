(ns day09
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [utils :as u]
            [hashp.core]))

(def data (->> "day09.dat"
               io/resource
               slurp
               str/split-lines
               (map read-string)
            ))
(def parts (partition 26 1  data))

(defn test [part]
  (let [target (last part)
        nums (take 25 part)
        workings     (for [x nums
                           y nums
                           :when (and  (not= x y) (= (+ x y) target))
                           ]
                       x)
        ]
    (if (empty? workings) target 0)
))

(def invalid-number  (first (filter #(not= 0 %) (map test parts))))



(defn test2 [range]
  (if (= invalid-number (apply + range))
    (+ (apply min range) (apply max range))
    nil))
       

(def part2ans (for [x (range 2 (count data))
      :when (not (empty? (filter #(not (nil? %)) (map test2 ( partition x 1 data)))))
      ]
(filter #(not (nil? %)) (map test2 ( partition x 1 data)))
))

(def table (atom ))
