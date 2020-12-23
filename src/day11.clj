(ns day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(def data (-> "day11.dat"
              io/resource
              slurp
              str/split-lines))

(def cols (count (first data)))
(def rows (count data))
(def board (into {} (map-indexed

        (fn [row line]
          (into {}  (map-indexed (fn [col char]
                         {[row col] char} 
                         ) line))
          )
        data ))
  )


(def neighbordeltas (for [x [-1 0 1] y [-1 0 1]
                         :when (not= 0 x y)] [x y]))

(def cell-bloc-3x3 (for [x [-1 0 1] y [-1 0 1]] [x y]))

(defn get-neighbor-coords [ [r c]]
  (map (fn [[dx dy]] [(+ r dx) (+ c dy)]) neighbordeltas
  ))

(defn build-board [state]
  (str/join "\n"  (for [r  (range rows)]
                    (str/join "" (for [c (range cols)](get state [r c])))
                    ))
  )


(defn get-neighbors-of-type [state neighbors char]
  (->>
   neighbors
   (map #(get state %  \.))
   (filter #(= % char))
  ))


(defn build-next-state [state]
  (into {}
  (map (fn [[[r c] value]]
         (let [neighbors (get-neighbor-coords [r c])
               available (count (get-neighbors-of-type  state  neighbors \L))
               seated (count (get-neighbors-of-type  state  neighbors \#))
               ]
           (cond
             (and (= value \L) (=  seated 0)) {[r c] \#}
             (and (= value \#) (>= seated 4)) {[r c] \L}
             :else {[r c] value}
             )))
       state)
  ))

(defn part1 [state]
  (loop [next-state (build-next-state state) old-state state generations 1]
    (if (= next-state old-state) next-state
        (recur (build-next-state next-state) next-state (inc generations))
        )
    )
  )
(def part1-ans (count (filter #(= \# %) (map #(get % 1) (part1 board)))))



(defn scan
  ([[r c]]  [r c])
  ([ [r c] [dr dc]] (lazy-seq (cons [ (+ r dr) (+ c dc)] (lazy-seq [(+ r dr) (+ c dc)] (scan [(+ r dr) (+ c dc)] [dr dc]))))))

;; (last (take-while  (fn [[r c]] (not= (get-in z [r c]) \Z)) (take 10 (scan [6 0] [0 1]))))

;; (defn get-seat [board [r c] [dr dc] ]
;;   (let [hit (take-while (fn [[rr cc]] (and (< rr rows) (< cc cols) (>= rr 0) (>= cc 0)
;;                                          (not= (get-in board [rr cc] ) \#)
;;                                          (not= (get-in board [rr cc] ) \L)
;;                                          )

;;                           ) (take 200 (scan [r c] [dr dc])))
;;         [hr hc] (if (empty? #p hit) [r c] (first hit))
;;         coord   [(+ dr hr) (+ dc hc)]
;;         ]
;;     (if (empty? hit) \. (get-in board coord))
;;   ))

(defn get-seat [board [r c] [dr dc]]
  (loop [testrow (+ r dr) testcol (+ c dc) ]
    (let [char (get-in board [testrow testcol] \Z)]
      (cond (= char \Z) \L
            (= char \#) \#
            (= char \L) \L
          :else (recur (+ testrow dr) (+ testcol dc))
          )
    )))
(def deltas [ [1 0] [-1 0] [0 1] [0 -1] [1 1] [-1 -1] [1 -1] [-1 1]])


(defn build-next-state2 [state]
  (let [newcells
    (for [r (range rows) c (range cols)]
      (let [
            seats  (map #(get-seat state [r c] %) deltas)
            occupied (count (filter #(or (= % \#) (= % \.)) seats))
            current (get-in state [r c])
            ]
        (cond (and (= current \L) (= occupied 0)) {[r c] \#}
              (and (= current \#) (>= occupied 5)) {[r c] \L}
              :else {[r c] current}
              )))
        ]
    (reduce (fn [board  v]
              (assoc-in board   (first (keys v)) (get v (first (keys v))))
              
              ) state newcells)
    ))
  


(defn part2 [state]
  (loop [next-state (build-next-state2 state) old-state state generations 1]
    (if (= next-state old-state) (build-next-state2  next-state)
        (recur (build-next-state2 next-state) next-state (inc generations))
        )
    )
  )
(def data2-raw (-> "day11.dat"
                   io/resource
              slurp
              str/split-lines
))
(def data2 (vec (map vec data2-raw)) )
(def cols (count (first data2)))
(def rows (count data2))

(def z (part2 data2))

(defn part2-ans [board]

  (apply + (->> board
           (map #(filter #{\#} % ) )
           (map count))))

