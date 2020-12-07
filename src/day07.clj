(ns day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [utils :as u]
            [hashp.core]

            [clojure.set :as set]))

(defn make-count-color [s]
  "ex: 1 bright white bag -> [:brightwight 1]" 
  (let [l (str/split s #" ")
        count (u/parse-int (first l))
        color (keyword (str/join ""  (take 2 (drop 1 l))))
        ]
    [color count]
    ))

(defn parse-right [b]
  " color count bags, color count bags... -> [[:color count][:color count]...]"
  (let [right (take 4  (str/split b #", "))
        c (map make-count-color right)
    ]
    c)
  )

(defn parse-line [line]
  " from string --> [ [:color count] [:color count] ... ] :first-color-in-string]"
  (let [ [a b] (str/split line #" contain ")
        left (keyword (str/join ""  (take 2 (str/split a #" "))))
        right (parse-right b)
        ]
    [right left]
    )
  )


(defn add-to-graph-part1  [graph [smallers bigger]]
  "adds a list representing a line from input to the graph but reverse the edges"
  (loop [g graph s (first smallers) r (rest smallers)]
    (let [color (first s)
          count (second s)]
      (if (nil? s)
        g
        (recur (update-in g [color] conj [ bigger count])  
               (first r) (rest r)))
          )))




(defn bfs1 [graph  fronteir visited]
  "breadth first search - use on the reversed edges from the part 1 graph"
  (let [current (first fronteir)
        neighbors (map first (filter #(not (contains? visited (first %))) (current graph)))
        new-visited (into visited neighbors)
        new-fronteir (into (rest  fronteir) neighbors)
        ]
    (if (empty? new-fronteir)
      new-visited 
      (recur graph new-fronteir new-visited)
      )
    )
  )


(defn add-to-graph-part2 [graph line]
  "just adds the line to the graph - doesn't reverse edges"
  (assoc graph (second line) (first line)) 
  )



(defn bfs2 [graph current visited level]
  "recursive bfs - do on the non-reversed edges for part 2"
  (let [raw-neighbors (current graph)
        neighbors (map first raw-neighbors)
        ]
    (if (empty? raw-neighbors)
      0
      (apply + 
             (for [n raw-neighbors]
               (+ ( second n) (* ( second n) (bfs2 graph (first n) (into visited neighbors) (inc level))))
               )))) )




(def data-raw 
  (-> "day07.dat"
      io/resource
      slurp
      str/split-lines
      ))

(def data
  (filter #(nil? ( re-find #"no other bags"%)) data-raw))

(def data-list (map parse-line data))

(def graph-part1 ( reduce add-to-graph {} data-list))
(def graph-part2 (reduce add-to-graph-part2 {} data-list))

(count (bfs1 graph [:shinygold]  #{}))
;; 233

(bfs2 graph-part2 :shinygold  #{} 0)
;; 421550


