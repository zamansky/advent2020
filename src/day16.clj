(ns day16
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(def data-string
  (-> "day16.dat"
      io/resource
      slurp
      (str/split #"\n\n")
      ))

(def categories-string (first data-string))
(def ticket-string (nth data-string 1))
(def nearby-string (last data-string))

(def my-ticket (map read-string  (-> ticket-string
                                     str/split-lines
                                     second
                                     (str/split #"," )
                                     )))

(def tickets 
(->> (drop 1 (str/split-lines nearby-string))
     (map #(str/split % #"," ))
     (map #(map read-string %))
     ))




(defn part1-parse-category [line]
  (let [[_ & vals] (re-find #".*: (\d+)-(\d+) or (\d+)-(\d+)" line)
        [x1 x2 x3 x4] (map read-string vals)
        r1 (range x1 (inc x2))
        r2 (range x3 (inc x4))
        ] 
    (into  (into #{} r1) r2)
    ))

(def cat-lines (->> categories-string
                    str/split-lines
                    (map part1-parse-category)
                    ))


(def all-values (reduce (fn [s next] (into s next) ) #{}  cat-lines))


(def nearby (->>  nearby-string
                  str/split-lines
                  (drop 1)
                  (map #(str/split % #"," ))
                  (map #(map read-string %))
                  ))

(def part1-ans (apply +
(->> (map (fn [x] (filter #(not (all-values %)) x)) nearby)
     (map #(apply + %)))
))

(defn valid-ticket [ticket all-values]
  (->> (map all-values ticket)
       (every? #(not (nil? %)))
  ))

(def valid-tickets  (filter #(valid-ticket % all-values) tickets))


(defn part2-parse-category [line]
  (let [[_ name & vals] (re-find #"(.*): (\d+)-(\d+) or (\d+)-(\d+)" line)
        [x1 x2 x3 x4] (map read-string vals)
        r1 (range x1 (inc x2))
        r2 (range x3 (inc x4))
        ] 
    {(keyword (str/replace name " " "-")) (into  (into #{} r1) r2)}
    ))

(def categories (into {}
   (->> categories-string
                    str/split-lines
                    (map part2-parse-category)
                    )))

(defn extract-row [tickets n]
  (reduce (fn [v item] (conj v (nth item n))) [] tickets))

(defn test-category [row category-set]
  (->>  (map category-set row)
        (every? #(not (nil? %))))
  )

(test-category (extract-row valid-tickets 0)   (:type categories))

(defn get-possible-categories [row categories]
  (let [t (extract-row valid-tickets row)
        cat-keys (keys categories)
        ]
  (reduce (fn [d item]
            (if (test-category t (item categories))
              (conj d item)
              d
            )) [] cat-keys)))
(def prereqs (for [r (range (count my-ticket))
      ]
  [r (get-possible-categories r categories)]
  ))

;; go through prereqs in sorted order

(def sorted-prereqs  (sort-by #(count (second %)) prereqs))
(defn solver [prereqs]
  (loop [prereqs prereqs sset {} ]
    (let [[row prereqset] (first prereqs)
          pre-to-solve (first prereqset)
          remaining-cands (rest prereqs)
          remaining (map (fn [[row s]]
                           [row (filter #(not (= pre-to-solve %)) s)]
                           ) remaining-cands )
          new-ss (assoc sset pre-to-solve row)
          
          ]
      (if (empty? #p remaining)
        new-ss
        (recur remaining new-ss)
        )
      
    )
    ))

(def fields  [:departure-platform
           :departure-time
           :departure-station
           :departure-track
           :departure-location
           :departure-date
           ])

(def s (solver sortqed-prereqs))

(def part2-ans (apply * (->> fields
                             (map #(% s))
                             (map #(nth my-ticket %))
                             )))
(map #(% s) fields)
(* 18 6 11 12 3 13)
