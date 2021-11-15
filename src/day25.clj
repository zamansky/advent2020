(ns day25)

(def modval 20201227)

(def card 5764801)
(def door 17807724)

(def real-card 15628416)
(def real-door 11161639)
(defn transform [ x num]
  (mod  (* x num) modval))

(defn calc-loop-size [key]
  (loop [x 1 count 0]
    (if (= x key)
      count
      (recur  (transform x 7) (inc count))
      )))

(def card-loop-size (calc-loop-size card))

(def door-loop-size (calc-loop-size door))

(transform door 11 )


(def cls (calc-loop-size real-card))
(def dls (calc-loop-size real-door))
(transform real-card cls)

(def part1 (loop [x 1 i 0]
             (if (= i cls) x (recur (transform x real-door) (inc i)))))
