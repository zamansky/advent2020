(ns day22
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(def raw-data  (-> "day22.dat"
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
  (loop [p1deck p1deck p2deck p2deck turn 0]
    (let [p1 (first p1deck)
          p1s (rest p1deck)
          p2 (first p2deck)
          p2s (rest p2deck)
          ]
      (cond (empty? p1deck) [p2deck turn :two]
            (empty? p2deck) [p1deck turn :one]
            (> p1 p2) (recur (concat p1s [p1 p2]) p2s (inc turn))
            :else  (recur p1s (concat p2s  [p2 p1]) (inc turn))
            ))))

(def part1-game-result (play p1 p2))
(def part1-ans (apply + (map * (first part1-game-result) (range (count (first part1-game-result)) 0 -1))))



(defn recur-combat [p1deck p2deck pastgames]
  (loop [p1deck p1deck p2deck p2deck turn 0 pastgames pastgames]
    (let [p1 (first p1deck)
          p1s (rest p1deck)
          p1count (count p1deck)
          p2 (first p2deck)
          p2s (rest p2deck)
          p2count (count p2deck)
          newpastgames (conj pastgames (list p1deck p2deck))
          ]
      (cond
        (pastgames (list p1deck p2deck)) [p1deck :p1 turn]
        (empty? p1deck) [p2deck :p2 turn]
        (empty? p2deck) [p1deck :p1 turn]
        (and (> p1count p1) (> p2count p2))
        (let [[cards winner turn] (recur-combat (take p1 p1s) (take p2 p2s) #{})]
          (if (= winner :p1)
            (recur (concat p1s [p1 p2]) p2s (inc turn ) newpastgames)
            (recur p1s (concat p2s  [p2 p1]) (inc turn) newpastgames)
            
            )
        )
        (> p1 p2) (recur (concat p1s [p1 p2]) p2s (inc turn) newpastgames)
        :else  (recur p1s (concat p2s  [p2 p1]) (inc turn) newpastgames)
        ))))

(def part2-game-result (recur-combat p1 p2 #{}))
(def part2-ans (apply + (map * (first part2-game-result) (range (count (first part2-game-result)) 0 -1))))

;; 35154

(defn -main [x]
(println part2-ans)
  )

