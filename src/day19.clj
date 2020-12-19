(ns day19
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]
            [instaparse.core :as insta]
            ))

(def raw-data (-> "day19.dat"
                  io/resource
                  slurp
                  (str/split #"\n\n")))

(def raw-rules (-> (first raw-data)
                   ))

(def raw-inputs (second raw-data))


(def parser (insta/parser raw-rules :start :0))

(def x
(->> raw-inputs
     str/split-lines

     (map parser)
     (filter (complement :index) )
     count 
     ))

(def s (str/split-lines raw-rules))
(def s2  (map (fn [l]
                (cond
                  (= (subs l 0 2) "8:") "8: 42 | 42 8"
                  (= (subs l 0 3) "11:") "11: 42 31 | 42 11 31"
                  :else l
                  )


                ) s))

(def parser2 (insta/parser (str/join "\n" s2) :start :0))

(def x
(->> raw-inputs
     str/split-lines

     (map parser2)
     (filter (complement :index) )
     count 
     ))
