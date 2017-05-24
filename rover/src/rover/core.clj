(ns rover.core
  (:require [clojure.set :refer [map-invert]]))

;; Don't forget tests!

(def direction-mapping-left {:north :west
                             :west :south
                             :south :east
                             :east :north})

(def direction-mapping-right (map-invert direction-mapping-left))

(defn left-turn [game]
  (let [direction (:direction game)]
    (assoc game :direction (get direction-mapping-left direction))))

(defn right-turn [game]
  (let [direction (:direction game)]
    (assoc game :direction ( get direction-mapping-right direction))))


(defn get-pos [game pos]
  (get-in (:world game) pos))

(def forward-mapping {:north [-1 0]
                      :south [1 0]
                      :west  [0 -1]
                      :east  [0 1]})

;; TODO: move on the world
(defn new-pos [world [x y] direction]
  (let [max-x (count world)
        max-y (count (first world))
        [diffx diffy] (get forward-mapping direction)]
    [(+ x diffx) (+ y diffy)]))

(def game {:world [[:_ :X :_ :_ :_ :X]
                   [:X :_ :_ :_ :_ :X]
                   [:_ :_ :_ :X :_ :X]
                   [:_ :_ :_ :_ :_ :_]
                   [:_ :_ :X :X :_ :X]
                   [:_ :_ :_ :_ :_ :_]], 
           :direction :north, 
           :position [3 3]})

(defn can-move? [game]
  (let [direction (:direction game)
        pos (:position game)
        world (:world game)
        neighbor (get-pos game (new-pos world pos direction))]
    (= neighbor :_)))

(defn move-forward [game]
  (if (can-move? game)
    (assoc game :position (new-pos (:world game) (:position game) (:direction game)))
    game))

(defn move-backward [game]
  (left-turn
    (left-turn
      (move-forward
        (left-turn
          (left-turn game))))))

(defn do-cmd [game cmd]
  (case cmd
    \l (left-turn game)
    \r (right-turn game)
    \f (move-forward game)
    \b (move-backward game)
    \q (System/exit 0)))

(defn execute
  "Given the current game state, execute the commands. Returns the next game
   state. You do not need to edit (:world game)."
  [game commands]
  (reduce do-cmd game commands))
