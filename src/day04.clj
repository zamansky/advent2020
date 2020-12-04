(ns day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]
            [utils :as u]
            [hashp.core]))

(def data (-> "day04.dat"
              io/resource
              slurp
              (str/split  #"\n\n")))

(defn str->pairs [item]
  (->> (str/split item #"[ \n]" )
       (map #(str/split % #":"))
       (map (fn [ [a b]] [(keyword "day04"  a) b]))))


(def data-map (->> (map str->pairs data)
                   (map #( into {} %))
                   (map #(dissoc % :day04/cid)) ;; take out cid since it's optional))

(def field-strings ["byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"] ) ;; cid is optional 
(def fields  (set  (map #(keyword "day04" %) field-strings))) 


(defn part1 [data-map]
  (->> data-map
       (filter #(= (set (keys  %)) fields))
       count))

(defn hgt-test [s]
  (let [[_ num-string units] (re-find #"([0-9]+)(cm|in)" s)
        num (u/parse-int num-string)]
    (cond (= units "cm") (and (>= num 150) (<= num 193))
          (= units "in") (and (>= num 59) (<= num 76))
          )))


(s/def ::byr (s/and string? #(>= (u/parse-int %) 1920) #(<= (u/parse-int %) 2002)))
(s/def ::iyr (s/and string? #(>= (u/parse-int %) 2010) #(<= (u/parse-int %) 2020)))
(s/def ::eyr (s/and string? #(>= (u/parse-int %) 2020) #(<= (u/parse-int %) 2030)))
(s/def ::hgt (s/and string? hgt-test))
(s/def ::hcl (s/and string? #(re-find #"#[0-9a-f]{6}" %)))
(s/def ::ecl (s/and string? #(re-find #"amb|blu|brn|gry|grn|hzl|oth" %)))
(s/def ::pid (s/and string? #(re-find #"^[0-9]{9}$" % )))
(s/def ::cid string?)
(s/def ::passport (s/keys
                   :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                   :opt [::cid]))
(part1 data-map)
(count (filter true? (map #(s/valid? ::passport %) data-map)))


