(ns day21
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [hashp.core]))


(defn parse-line [line]
  (let [[_ ingredients-string allergens-string]  (re-find #"(.*) \(contains (.*)\)" line)
        ingredients (str/split ingredients-string #" ")
        allergens (str/split allergens-string #", ")
        ] 
  [ingredients  allergens]))

(def data  (->> "sample21.dat"
               io/resource
               slurp
               (str/split-lines)
               (map parse-line)
               ))

(def allergens (->> data
                    (map second)
                    flatten
                    set))

(def ingredients (->> data
                      (map first)
                      flatten
                      set))

(def allersets1  (reduce (fn [d [ing alerg]]
                         (loop [a (first alerg) as (rest alerg) d d]
                           (cond (nil? a) d
                                 :else (recur (first as) (rest as) (update d a into ing))
                                 
                                 )
                           )) {} data))


(def allersets (into {} (map (fn [[k v]] [k (set v)]) allersets1)))


(def foodsets1 (reduce (fn [dict [ing aler]]
                         (loop [dict dict i (first ing) is (rest ing)]
                           (cond (nil? i) dict
                                 :else (recur (update dict i into aler) (first is) (rest is))
                                 ))){} data))

(def foodsets (into {} (map (fn [[k v]] [k (set v)]) foodsets1)))


(def d (map (fn [ [i a]]
              [ (set i) (set a)]) data))


  )
