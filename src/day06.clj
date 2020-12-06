(ns day06
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))


(def data (-> "day06.dat"
              io/resource
              slurp
              (str/split  #"\n\n")))


(defn f [x]
  (count  (into #{} (str/replace x #"\n" ""))))

(apply + (map f data))

(defn process-family [f]
  (reduce set/intersection (map (fn [x]
                                  (into #{} x))
                                (str/split-lines f))))

(reduce + (map count (map process-family data)))
