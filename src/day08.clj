(ns day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [utils :as u]))


(def data (-> "day08.dat"
              io/resource
              slurp
              str/split-lines))

(defn parse-line [i line]
  (let [ [opcode  param-string] (str/split line #" ")]
    [i (keyword opcode) (u/parse-int param-string)]))

(map parse-line data)

(defn data->program [data]
  (map-indexed parse-line data))

(def program  (into [] (data->program data)))


(def state {:ip 0 :acc 0})

(defn do-nop [param state]
  (update state :ip inc ))

(defn do-jmp [param state]
  (update state :ip #(+ param %)))

(defn do-acc [param state]
  (-> state
      (update :ip inc)
      (update :acc #(+  param %))
  ))

(def ops
  {:acc do-acc
   :nop do-nop
   :jmp do-jmp})

( (:nop ops) 6 state )

(defn run-program [program state]
  (loop [state state visited {}]
    (cond (not (nil?  (get visited (:ip state) ))) state
          (= (:ip state) (count program)) state          
          :else  (let [ip (:ip state)
                       [lineno op param] (nth program ip)
                       newstate ( (op ops) param state)
                       newvisited (assoc visited ip true)
                      ]
                   (recur newstate newvisited)
                  ))))

;; (run-program program state)
;;;(get program )

(defn test-program [program state]
  (loop [state state visited {}]
    (cond (not (nil?  (get visited (:ip state) ))) false
          (= (:ip state) (count program)) true
          :else  (let [ip (:ip state)
                       [lineno op param] (nth program ip)
                       newstate ( (op ops) param state)
                       newvisited (assoc visited ip true)
                      ]
                   (recur newstate newvisited)
                  ))))

(defn convert-candidates [progam from to]
  (->> program
       (filter #(= (nth % 1) from))
       (map (fn [ [a b c ]] [a to c]))
       ))
                 
(defn swap-instruction [program [lineno op param ]]
  (assoc program lineno [lineno op param])
  )

(def nop-candidates (convert-candidates program :nop :jmp))
(def jmp-candidates (convert-candidates program :jmp :nop))


(swap-instruction program (first nop-candidates))





(filter (fn [s] (test-program (swap-instruction program s) state)) nop-candidates)
(def only (first  (filter (fn [s] (test-program (swap-instruction program s) state)) jmp-candidates)))

(nth program 265)
(def newprog (swap-instruction program only))
(nth newprog 265)

(run-program newprog state)

(def p2 (swap-instruction))

