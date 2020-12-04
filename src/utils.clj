(ns utils)


(defn load-data [filename]
  (slurp (str "data/" filename)))

(defn parse-bigint [s]
  "string->int"
  (biginteger (re-find  #"\-?\d+" s )))



(defn parse-int
  "string->int"
  ([s] (parse-int s nil))
  ([s default]
  (try (Integer. (re-find  #"\-?\d+" s ))
       (catch Exception r default))
  ))


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
