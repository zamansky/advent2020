(ns day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(def d
  (->>"sample10-1.dat"
      io/resource
      slurp
      str/split-lines
      (map read-string)
      sort
      vec))

(def data  (into  d  [(+ 3 (apply max d))]))




(defn add-jolts [current jolts working-set]
  (reduce (fn [j item]

            (update j  (dec (- item current)) inc))
          jolts working-set)
  )



(defn part1 [data]
(loop [j [0 0 0] 
       current 0
       item (first data)
       r (rest data)]
(let [index (dec (- item current))
      ]
  (cond (empty? r) (update j index inc)
        :else (recur (update j index inc) item (first r) (rest r))
        )))
)

(defn data->map [data]
  (println "HELLO")
  (loop [graph {}
         current 0
         targets (take-while #(<= (- % current) 3) data)
         r data]
    (cond
      (empty? r) graph
      :else (recur (assoc graph current targets)
                   (first r)
                   (take-while #(<= (- % (first r)) 3) (rest r))
                   (rest r)
                   )
      )
    )
  )

(defn data->reverse-map [data]
  (loop [graph {}
         current (first (-> data reverse vec (into [0])))
         targets (vec (take-while #(<= (- current %) 3) (drop 1 (->  data reverse vec (into [0])))))
         r (rest  (-> data reverse vec (into [0])))]
    (cond
      (empty? r) (assoc graph current targets)
      :else
      (do
        (recur (assoc graph current targets)
                   (first r)
                   (vec (take-while #(<= (-  (first r) %) 3) (rest r)))
                   (rest r)
                   ))
      )
    )
  )

(defn traverse [g current]
  (let [neighbors  (get g current [])]
    (if (empty? neighbors) 0
         (reduce + (map  #(traverse g % ) neighbors))
         )

    )
  )
