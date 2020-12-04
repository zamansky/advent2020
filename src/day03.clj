(ns day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]
            ))

(def data  (->> (io/resource "day03.dat")
                slurp
                str/split-lines))

(defn build-candidates [data [dx dy]]
  (let [rows (count data)]
    (for [r (range rows)
          :when (<= (* r dy) rows)
          ]
      [(* r dy) (* r dx) ])))

(defn test-candidate [data [row col] ]
  (let [num-rows (count data)
        num-cols (count (first data))
        new-col (mod col num-cols)
        row_string (nth data row)
        c (nth row_string new-col)
        ]
    c
    ))


(defn part1 [data slope]
  (->> (build-candidates data slope)
       (map (partial test-candidate data))
       (filter #(= % \#))
       count
       ))

(part1 data [3 1])
(def slopes [ [1 1] [3 1] [5 1] [7 1] [1 2]])

(defn part2 [data slopes]
(->>
 (map (partial part1 data) slopes)
 (reduce *)
 ))

(part2 data slopes)
