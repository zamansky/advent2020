(ns day02
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [utils :as u]
            [hashp.core]
            [clojure.string :as str]))


(def parser #"(\d+)-(\d+) ([a-z]): ([a-z]*)")
(line-seq (io/reader (io/resource "day02.dat")))

(slurp "data/day02.dat")
(def data
  (->>
   ;;(slurp "data/sample02.dat")
   (slurp "data/day02.dat")
   string/split-lines
   (map #(re-find parser %))
   (map (fn [ [_ min max letter password]]
        {:min (u/parse-int min) :max (u/parse-int max) :letter letter :password password}
          ) )
))

(defn check-password-part1 [{:keys [min max letter password ]}]
  (let [counts (frequencies password)
        l (first letter) ;; string -> char
        count (get counts l 0)]
    (and (>= count min)
         (<= count max))
    ))



(def part1-passwords (map check-password-part1 data))
(def part1 (count (filter true? passwords)))

(defn check-password-part2 [{:keys [min max letter password ]:as line}]
  (let [range (subs password (dec min) max)
        counts (frequencies range)
        l (first letter)
        ]

    (and
     (or
      (= (get password (dec min)) l)
      (= (get password (dec max)) l))
     (not= (get  password (dec min)) (get password (dec max)))
    )))

(def part2-passwords (map check-password-part2 data))
(def part2 (count (filter true? part2-passwords))); 364

