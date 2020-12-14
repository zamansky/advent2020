(ns day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.core]))

(def data (->> "sample12.dat"
              io/resource
              slurp
              str/split-lines
              (map #(first ( re-seq #"([A-Z])(\d+)" %)))
              (map (fn [[_ b c]] [(keyword b) (read-string c)]))
              ))

(def vals {:E [-1 0] :W [1 0] :N [0 1] :S [0 -1]} )
(def dirs { 0 :E 90 :N 180 :W 270 :S})
(def state {:pos [0 0] :dir 0 :waypoint [-10 1]})



(def rules-part1
  {:E (fn [{:keys [pos dir] :as state} opt] (assoc state :pos (map + pos (map #(* opt %) (:E vals)))))
   :W (fn [{:keys [pos dir] :as state} opt] (assoc state :pos (map + pos (map #(* opt %) (:W vals)))))
   :N (fn [{:keys [pos dir] :as state} opt] (assoc state :pos (map + pos (map #(* opt %) (:N vals)))))
   :S (fn [{:keys [pos dir] :as state} opt] (assoc state :pos (map + pos (map #(* opt %) (:S vals)))))
   :F (fn [{:keys [pos dir] :as state} opt] (assoc state :pos (map + pos (map #(* opt %) ((get dirs dir) vals)))))
   :L (fn [{:keys [pos dir] :as state} opt] (assoc state :dir (mod (+ dir opt) 360)))
   :R (fn [{:keys [pos dir] :as state} opt] (assoc state :dir (mod (- dir opt) 360)))
   
   }
  )
(def part1-ans (->>
 data
 (reduce (fn [state [cmd opt]] ( (rules-part1 cmd) state opt)) state)
:pos
 (map #( Math/abs %))
 (apply +)
 ))


(defn deg->rad [degree] (* degree (/ Math/PI 180)))

(defn rotate [ [px py] [x y] degrees]
  (let [theta (deg->rad degrees)
        x' (- (* x (Math/cos  theta)) (* y (Math/sin theta)))
        y' (+ (* y (Math/cos theta)) (* x (Math/sin theta)))
        ]
    [(Math/round x') (Math/round y')]
  ))


(def rules-part2 {:E (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :waypoint (map + waypoint (map #(* opt %) (:E vals)))))
                  :W (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :waypoint (map + waypoint (map #(* opt %) (:W vals)))))
                  :N (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :waypoint (map + waypoint (map #(* opt %) (:N vals)))))
                  :S (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :waypoint (map + waypoint (map #(* opt %) (:S vals)))))
                  :F (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :pos (map + pos (map #(* opt %) waypoint))))
                  :L (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :waypoint (rotate pos waypoint (* -1 opt))))
                  :R (fn [{:keys [pos dir waypoint] :as state} opt] (assoc state :waypoint (rotate pos waypoint (* 1 opt))))

           }
  )

(def part2-ans  (->>
                 data
                 (reduce (fn [state [cmd opt]] ( (rules-part2 cmd) state opt)) state)
                 :pos
                 (map #( Math/abs %))
                 (apply +)
                 ))


