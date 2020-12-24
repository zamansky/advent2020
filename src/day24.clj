(ns day24 (:require [clojure.java.io :as io]
                    [hashp.core]
            ))


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
