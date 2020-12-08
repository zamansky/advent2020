(ns utils)

(defn parse-int [s]
  "string->int"
  (biginteger (re-find  #"\-?\d+" s )))

(defn abs [n] (max n (- n)))

(defn char->int [c]
  (Long/parseLong (String/valueOf c)))

(defn gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm [a b]
  (/ (abs (* a b))
     (gcd a b)))
