(ns day14
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(defn parse-instr [[cmd param]]
  (if (= "mask" cmd)
    {:instr :mask :param param}
    (let [ [_ dst] (re-find #"mem\[(\d+)\]" cmd)]
      {:instr :mem :param param :dst dst}
  )))

(def data (->> "day14.dat"
               io/resource

               slurp
               str/split-lines
               (map  #(re-find #"(.+) = (.+)" %))
               (map #(drop 1 %))
               (map parse-instr)
               ))

(def state {:mem {} :and-mask 0 :or-mask 0})



(defn make-and-mask [s]
  (->>
   (map {\X \1 \1 \1 \0 \0} s)
   (apply str "2r")
   read-string
   ))

(defn make-or-mask [s]
  (->>
  (map {\X \0 \1 \1 \0 \0} s)
  (apply str "2r")
  read-string))


(def m "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
(bit-or (make-and-mask m) 23)

;; replace x with 0
;; 0 or 1 = 1
;; 0 or = 0 so theys stay the same
;; 1 will turn on
(defn mask2-or [s] (map {\1 \1 \x \1 \0 \0} s))

(defn process-cmd [{:keys [and-mask or-mask] :as state}
                   {:keys [instr dst param] :as instr}]
  (cond
    (= instr :mask) (assoc state :and-mask #p (make-and-mask param)
                           :or-mask (make-or-mask param))
    (= instr :mem) (assoc-in state [:mem   dst]
                             (-> param
                                   read-string
                                   (bit-or or-mask)
                                   (bit-and and-mask)
                                 ))
    )
  )

(def init-machine (reduce process-cmd state data))

(def mem (:mem init-machine))
(def part1-ans) (apply +  (map second mem))
