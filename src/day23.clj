(ns day23
  (:require [clojure.string :as str]
            [hashp.core]))

;; "389125467"
;; real "469217538" 
(defn s->i [s] (map read-string s))
(def state (-> "469217538" 


               (str/split #"")
               s->i
               ))



(defn find-target [tmpstate]
  (let [dest (dec (first  tmpstate))]
    (loop [dest dest]
    (let [i (.indexOf tmpstate dest)]
      (cond
       (= i -1) (recur (mod (dec dest) 10))
        :else i
)))))

(defn next-state [state]
  (let [[current & remaining ] state
        holdout (take 3 remaining)
        leftover (drop 3 remaining)
        tmpstate (cons current leftover)
        i (find-target tmpstate)
        ]
    (concat (take i leftover)
            holdout
            (drop i leftover)
            (list current))
)
  )

(defn part1-ans [state moves]
  (let [final-state (reduce (fn [state ns] (next-state state)) state (range moves))
        i (.indexOf final-state 1)
        newend  (take i final-state)
        newstart (rest (drop i final-state))
        
        ]
    (concat newstart newend)
    )
  )






(defn part2-find-next [current  removed]
    (loop [dest (dec  current)]
      (cond  (= dest 0) (recur 1000000)
            (  (set  removed)  dest ) (recur (dec dest))
            :else dest
      )))

(defn part2 [state start moves]
  (loop [current start state state turn  (dec  moves)]
    (let [removed  [(state current) (-> (state current) state) (-> (state current) state state)]
          ;; tmpstate (assoc  state current (state (last removed)))
          i  (part2-find-next current removed)
          nextcurrent (state (last removed))
          nextstate       (-> state
                              (assoc current (state (last removed)))
                              (assoc (last removed) (state i))
                              (assoc i (first removed))
                              )
          ]
      (cond (= turn 0) nextstate
            :else (recur  nextcurrent  nextstate (dec turn))
    ))))

(defn get-state-list [state start]
  (loop [result [start] remaining state current start]
    (let [next (remaining current)
          nextrem  (dissoc remaining current)
          nextres (conj result next)
          ]
      (cond (empty? remaining)  (drop-last result)
          :else (recur nextres nextrem next)
    ))
  ))

(def restoflist (range 10 1000001))
(def state2 (concat state restoflist))
 (def state2-dict
   (-> (reduce (fn  [state [k v]] (assoc state k v )) {} (partition 2 1 state2))
        (assoc  (last state2) (first state2))
        ))


(def z (part2 state2-dict 4 10000000))

(def part2-ans  (* (z 1) (z (z 1))))

(defn -main []
  (let [z (part2 state2-dict 4 10000000)]
    (println (z 1)))
    )



