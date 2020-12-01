(ns day01
  (:require [clojure.string :as string]
            [utils :as u]))

(def data
  (->> 
   (slurp "data/sample01.dat")
   ;; (slurp "data/day01.dat")
    string/split-lines
       (map u/parse-int)
       ))

(defn checksum [x] (first (filter #(= 2020 (+ % x)) data)))
(def part1  (reduce * (filter #(not ( nil? %)) (map  checksum  data ))))


(def part1-again (reduce * (filter  (fn [x]  (some #(= (- 2020 x) %) data)) data)))

(def twosum  (flatten (map (fn [x] (map #(+ % x) data)) data)))
(def part2-numbers  (set (filter #(not ( nil? %)) (map  checksum  twosum ))))
(def part2 (reduce * part2-numbers))

(def part2-numbers-again (set  (filter  (fn [x]  (some #(= (- 2020 x) %) twosum)) data)))
(def part2-again (reduce * part2-numbers-again))
  
