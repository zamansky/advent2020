(ns day14)

(defn make-and-mask [s]
  (map {\X \1 \1 \1 \0 \0} s)
  )

(defn make-or-mask [s]
  (->
   (map {\X \0 \1 \1 \0 \0} s)
  
  
  )



(def m "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
(bit-or (make-and-mask m) )

;; replace x with 0
;; 0 or 1 = 1
;; 0 or = 0 so theys stay the same
;; 1 will turn on
(defn mask2-or [s] (map {\1 \1 \x \1 \0 \0} s))

