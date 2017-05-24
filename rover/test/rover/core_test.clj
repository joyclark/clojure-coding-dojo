(ns rover.core-test
  (:require [clojure.test :refer :all]
            [rover.core :as rover]))

(def game {:world [[:_ :X :_ :_ :_ :X] 
                   [:X :_ :_ :_ :_ :X] 
                   [:_ :_ :_ :X :_ :X] 
                   [:_ :_ :_ :_ :_ :_] 
                   [:_ :_ :X :X :_ :X] 
                   [:_ :_ :_ :_ :_ :_]], 
           :direction :north, 
           :position [3 3]})

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest test-can-move?
  (testing "Can I move?"
    (is (not (rover/can-move? game)))))

(deftest test-i-can-move
  (testing "I can move!"
    (is (rover/can-move? (rover/execute game "l")))))

(deftest test-turn-right
  (testing "test turn right"
    (let [one-turn (rover/execute game "r")]
      (is (= (:direction one-turn) :east)))))

(deftest test-turn-left-right
  (testing "stay in place"
    (let [stay (rover/execute game "lr")]
      (is (= (:direction stay) :north)))))

(deftest test-turn-left
  (testing "test create a new game"
    (let [one-turn (rover/execute game "l")
          two-turns (rover/execute game "ll")
          three-turns (rover/execute game "lll")
          four-turns (rover/execute game "llll")]
      (is (= (:direction one-turn) :west))
      (is (= (:direction two-turns) :south))
      (is (= (:direction three-turns) :east))
      (is (= (:direction four-turns) :north)))))

(deftest test-move-forward
  (testing "test moving forward"
    (let [no-move (rover/execute game "f")
          left-forward (rover/execute game "lf")
          l-f-r-f (rover/execute game "lfrf")
          l-f-r-f-f-r-f (rover/execute game "lfrffrf")]
      (is (= game no-move))
      (is (= (:position left-forward) [3 2]))
      (is (= (:position l-f-r-f) [2 2]))
      (is (= (:position l-f-r-f-f-r-f) [1 3])))))
