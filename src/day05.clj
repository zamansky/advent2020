(ns day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [utils :as u]))


(defn string->seat-helper [i l]
  (let [max (reduce * (repeat i 2 ))
        ]
    ;; F and L indicate the lower part of hte range so subtract a number
    ;; otherwise subtract 0
    (if (or  (= l \F) 
             (= l \L))
      max
      0)))




(defn find-seat [seat]
  (let [row (reverse (take 7 seat))
        col (reverse (drop 7 seat))
        row-num (reduce - 127 (map-indexed string->seat-helper row))
        col-num (reduce - 7 (map-indexed string->seat-helper col))
        ]
    (+ (* row-num 8) col-num)))



(map-indexed string->seat-helper "FBFBBFF")

(def seats ["BFFFBBFRRR","FFFBBBFRRR","BBFFBBFRLL"])

(def seat "FBFBBFFRLR")

(find-seat seat)

(apply max (map find-seat seats))

(def data (-> "day05.dat"
              io/resource
              slurp
              str/split-lines))
(apply max  (map find-seat data))

(def ids (map find-seat data))
(def s (set (sort ids)))
(def min-seat  (apply min ids))
(def max-seat (apply max ids))

(take 20 s)

(defn is-empty [seat]
          (and (not (contains? s seat))
                    (contains? s (inc seat))
                    (contains? s (dec seat))))

(filter is-empty (range (dec min-seat) (inc max-seat)))

(filter (fn [ [a b c] ]
          (not= (inc a) b (dec c))
          ) (partition 3 1 (sort ids)))

(reverse  (take 5 seat))



(def vals {\B 1 \F 0 \R  1 \L 0})

(def seatint(map #(get vals %) seat)))
(defn exp [a b] (reduce * (repeat b a)))
(reduce + (map-indexed (fn [i v] (* v (exp 2 i))) seatint))

(defn find-seat-better [s]
  (->> s
       reverse
       (map #(get vals %))
       (map-indexed (fn [i v] (* v (exp 2 i))))
       (reduce +)
   )
  )

(find-seat-better seat)
