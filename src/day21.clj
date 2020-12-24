(ns day21
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))


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

(set/intersection (get allersets "dairy") (get allersets "fish"))

(map (fn [food]
       (reduce (fn [aset [k v]] ) #{} allersets)

       ))
