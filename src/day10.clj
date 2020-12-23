(ns day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(def d
  (->>"sample10-2.dat"
      io/resource
      slurp
      str/split-lines
      (map read-string)
      sort
      vec))

(def data  (into  d  [(+ 3 (apply max d))]))






(defn part1 [data]
  (loop [j [0 0 0] 
         current 0
         item (first data)
         r (rest data)]
    (let [index (dec (- item current))]
      (cond (empty? r) (update j index inc)
            :else (recur (update j index inc) item (first r) (rest r))
            ))))
(part1 data)


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

(def rmap (data->reverse-map data))

(def ways (atom {}))

(doseq [d  data]
        (let [
              n (get rmap d)
              nway   (apply + (map #(get @ways % 1) n))
              ]
          ;;          (swap! ways assoc d (apply max [nway w]))
                    (swap! ways assoc d nway))
          ))

(get  (reduce
 (fn [ways d]
   
        (let [
              n (get rmap d)
              nway   (apply + (map #(get ways % 1) n))
              ]
          ( assoc ways d nway)
          
          )) {} data ) (last data))



