(ns day17
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(def data (-> "day17.dat"
              io/resource
              slurp
              str/split-lines))


(def board
  (into #{}        (reduce concat
                           (map-indexed
                            (fn [row line]
                              (->> line
                                   (map-indexed (fn [col char]
                                                  (if (= char \#) [row col 1])))
                                   (filter #(not (nil? %)))
                                   
                                   )
                              ) data))))

(def board2
  (into #{}        (reduce concat
                           (map-indexed
                            (fn [row line]
                              (->> line
                                   (map-indexed (fn [col char]
                                                  (if (= char \#) [row col 1 1])))
                                   (filter #(not (nil? %)))
                                   
                                   )
                              ) data))))


(defn data->board [data dims]
  (into #{}        (reduce concat
                           (map-indexed
                            (fn [row line]
                              (->> line
                                   (map-indexed (fn [col char]
                                                  (if (= char \#)
                                                    (concat  [row col] (vec (repeat (- dims 2) 1)))
                                                    )))
                                                    
                                   (filter #(not (nil? %)))
                                   
                                   )
                              ) data))))


(defn build-neighbor-coords2 [[row col depth zepth]]
  (for [r (range (dec row) (+ row 2))
        c (range (dec col) (+ col 2))
        d (range (dec depth) (+ depth 2))
        z (range (dec zepth) (+ zepth 2))
        :when (not (and (= r row) (= c col) (= d depth) (= z zepth)))]
    [r c d z]
    ))

(defn get-neighbors2 [ board [row col depth zepth]]
(let [candidates (build-neighbor-coords2 [row col depth zepth])]
    (->> (map #(board %) candidates)
         (filter #(not (nil? %))))
    )
  )

(defn get-all-neighbors2 [board]
  (reduce (fn [s n]
            (into s (build-neighbor-coords2 n ))) #{} board)
)

(defn is-alive2? [board coord]
(let [n (count (get-neighbors2 board coord))]
      (cond (and  (board coord) (or (= n 2) (= n 3))) true
            (and  (not (board coord)) (= n 3))true
            :else false
            )))



(defn build-next-gen2 [board]
(let [coords  (get-all-neighbors2 board)
      
      ]
  (into #{} (->>  coords
                 (map (fn [x] (if (is-alive2? board x) x )))
                 (filter #(not (nil? %)))
                 ))
  ))

(def part1-ans (count  (-> board
                           build-next-gen
                           build-next-gen
                           build-next-gen
                           build-next-gen
                           build-next-gen
                           build-next-gen

                           )))

(def part2-ans (count  (-> board2
                           build-next-gen2
                           build-next-gen2
                           build-next-gen2
                           build-next-gen2
                           build-next-gen2
                           build-next-gen2

                           )))
