(ns day15
  (:require [clojure.string :as str]
            [hashp.core]))


(def hist {9 1
           6 2
           0 3
           10 4 
           18 5 
           2 6
           })

(defn next-move [ [hist lastmove turn ]]
  (let [lasttime (hist lastmove)
        nextmove (if lasttime (- (dec turn) lasttime) 0)
        newhist (assoc hist lastmove (dec turn))
        ]
    [newhist nextmove (inc turn)]
))

(def ans (loop [ hist hist lastmove 1 turn 8]
       (let [ [newhist nextmove nextturn] (next-move [ hist lastmove turn])

             ]
         (if (= nextturn 30000001) [newhist nextmove nextturn]
             (recur newhist nextmove nextturn)
             )
         )))



       
       ;; part1 1238
       
(time (loop [ hist hist lastmove 1 turn 8]
       (let [ [newhist nextmove nextturn] (next-move [ hist lastmove turn])

             ]
         (if (= nextturn 30000001) [newhist nextmove nextturn]
             (recur newhist nextmove nextturn)
             )
         )))
