(ns day18
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn str->sexp [s]
  (read-string (str "(" s ")")))

(def data
  (->> "day18.dat"
    io/resource
    slurp
    str/split-lines
    (map str->sexp)
  )  )

(def s "1 + (2 * 3) + (4 * (5 + 6))")

(def s2 (read-string (str "(" s ")")))

(defn prefix-expression
  "place function as the first item in each sexp"
  [function exp]
  (cons function (map #(if (list? %) (prefix-expression function %) %) exp)))

(defn part1-eval
  "evaluate left to right using reduce -> convert triples to prefix and eval"
  [ f & r]
  (reduce (fn [ans [op next]]
            (apply op [ans next] )) f (partition 2 r))
  )


(def s3 "2 * 3 + (4 * 5)")
(def s4 (read-string (str "(" s3 ")")))
(defn part2-eval [ & params]
  (->> (partition-by #(= % *) params)
       (map #(filter number? %))
       (filter #(not (empty? %)))
       (map #(apply + %))
       (reduce *)
       )
  
  )


(def part1-ans
  (apply +
         (->> data
              (map #(eval (prefix-expression part1-eval %)))
              )))

(def part2-ans
  (apply +
         (->> data
              (map #(eval (prefix-expression part2-eval %)))
              )))
