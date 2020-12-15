(ns demoday14
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-instr [ [cmd param]]
  (if (= cmd "mask")
    {:instr :mask :param param}
    (let [ [_ dst] (re-find #"mem\[(\d+)\]" cmd)  ]
    {:instr :mem :param param :dst dst}
    )))

(def data (->> "day14.dat"
               io/resource
               slurp
               str/split-lines
               (map #(re-find #"(.+) = (.+)" %) )
               (map #(drop 1 %))
               (map parse-instr)
               ))

(defn make-and-mask [s]
  (->> s
   (map {\X \1 \1 \1 \0 \0})
   (apply str "2r")
   read-string
   ))

(defn make-or-mask [s]
  (->> s
   (map {\X \0 \1 \1 \0 \0})
   (apply str "2r")
   read-string
   ))


(def state {:mem {} :and-mask 0  :or-mask 0 :mask ""})
(defn part1-process-instr [{:keys [and-mask or-mask] :as state}
                           {:keys [instr dst param] :as instr}]
  (cond
    (= instr :mask) (assoc state
                           :and-mask (make-and-mask param)
                           :or-mask (make-or-mask param))
    (= instr :mem)  (assoc-in state [:mem dst]
                              (-> param
                                  read-string
                                  (bit-or or-mask)
                                  (bit-and and-mask)))
    ))


(def part1-machine (reduce part1-process-instr state data))
(def mem1 (:mem part1-machine))
(def part1-ans (apply + (map second mem1)))

(defn build-nums [s]
  (if (some #{\X} s)
    (let [s1 (str/replace-first s \X 1)
          s2 (str/replace-first s \X 0)
          ]
      (conj [] (build-nums s1) (build-nums s2))
      )s))


(defn dst->binary-string [dst]
  (let [s (Integer/toBinaryString (read-string dst))
        s2 (str (apply str (repeat 36 \0)) s)
        l (count s2)
        result (subs s2 (- l 36))
        ]
    result))

(defn apply-mask [mem mask]
  (->>
   (map (fn [mem-item mask-item]
          (cond
            (= mask-item \0) mem-item
            (= mask-item \1) \1
            :else \X
            )) mem mask)
   (apply str)))

(defn part2-process-instr [{:keys [mask] :as state}
                           {:keys [instr dst param] :as instr}]
  (cond
    (= instr :mask) (assoc state :mask param)
    (= instr :mem) (let [bin-mem (dst->binary-string dst )
                         bin-mem-masked (apply-mask bin-mem mask)
                         mem-candidates (flatten (build-nums bin-mem-masked))
                         ]
                     (reduce (fn [state candidate]
                               (assoc-in state [:mem candidate] (read-string param)))
                             state mem-candidates
                             )
                     )))



  
(def part2-machine (reduce part2-process-instr state data))
(def mem2 (:mem part2-machine))
(def part2-ans (apply + (map second mem2)))
