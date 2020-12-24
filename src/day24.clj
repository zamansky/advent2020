(ns day24 (:require [clojure.java.io :as io]
                    [hashp.core]

                    [clojure.string :as str]))


(def data (-> "day24.dat"
              io/resource
              slurp
              str/split-lines))

(def line "sesenwnenenewseeswwswswwnenewsewsw")

(defn parse-line [line]
  (loop [ steps [] x (first line) line line]
    (cond (nil? x) steps
          (or  (= x \e) (= x \w)) (recur (concat steps [(str x)]) (first (rest line)) (rest line))
          :else (let [step (apply str (take 2 line))
                      tmpline (drop 2 line)]
                  (recur (concat steps [step]) (first tmpline) tmpline)
                  )
  )))
(def deltas {"e" [-2 0]
             "w" [2 0]
             "ne" [-1 -1]
             "nw" [1 -1]
             "se" [-1 1]
             "sw" [1 1]})

(def tile-loc (parse-line line))
(defn get-tile-location [steps]
(reduce (fn [loc step]
          (map + loc (deltas  step))
          ) [0 0] steps))

(defn flip-tile [tiles loc]
  (if (=  (get tiles loc 0) 0)
    (assoc tiles loc 1)
    (assoc tiles loc 0)
    ))


(def raw-world (reduce (fn [tiles loc]
                  (flip-tile tiles loc))
                {}
                (->> (map parse-line data) (map get-tile-location))))

(def world  (set (keys (filter (fn [[k v]] (= v 1)) raw-world))))

(def part1-ans (count  world))


(defn get-neighbor-coords [loc]
    (map  #(map + loc %) (vals deltas))
    ))

(defn get-all-neighbor-coords [world]
  (reduce (fn [s n]
            (into s (get-neighbor-coords n))) (set world)  world))


(defn next-color [world coord]
  (let [candidates (get-neighbor-coords coord)
        count-alive (->>  (map #(world %) candidates)
                          (filter #(not (nil? %)))
                          count)
        current (if (world coord) 1 0)
        ]
    (cond (and  (= current 1)
                (or  (= 0 count-alive)
                     (> count-alive 2))) 0
          (and (=  current 0)
               (= 2 count-alive)) 1
          :else current 
    )

    ))


(defn next-gen [world]
  (let [candidates (get-all-neighbor-coords world)
        next-vals (map #(if (= 1 (next-color world %)) %) candidates)
        ]
    (set (filter #(not (nil? %)) next-vals))))

(def part2-ans 
(count  (loop [world world times 100]
          (if (= times 0) world
              (recur (next-gen world) (dec times))
              ))))
