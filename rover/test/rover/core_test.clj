(ns rover.core-test
  (:require [clojure.test :refer :all]
            [rover.core :as rover]))

(def world [[:_ :X :_ :_ :_ :X]
            [:X :_ :_ :_ :_ :X]
            [:_ :_ :_ :X :_ :X]
            [:_ :_ :_ :_ :_ :_]
            [:_ :_ :X :X :_ :X]
            [:_ :_ :_ :_ :_ :_]])

(def game {:world world,
           :direction :north,
           :position [3 3]})

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
  (testing "what happens when I move forward?"
    (let [test-game {:world world :direction :north :position [2 2]}
          moved-back (rover/execute test-game "f")]
      (is (= (:position moved-back) [1 2])))))

(deftest test-move-forward-cant
  (testing "what happens when I can't move forward?"
    (let [test-game {:world world :direction :north :position [3 3]}
          moved-back (rover/execute test-game "f")]
      (is (= (:position moved-back) [3 3])))))

(deftest test-move-backward
  (testing "what happens when I move backward?"
    (let [test-game {:world world :direction :north :position [2 2]}
          moved-back (rover/execute test-game "b")]
      (is (= (:position moved-back) [3 2])))))

(deftest test-move-backward-cant
  (testing "what happens when I can't move forward?"
    (let [test-game {:world world :direction :north :position [3 3]}
          moved-back (rover/execute test-game "b")]
      (is (= (:position moved-back) [3 3])))))

(deftest test-move
  (testing "test moving forward"
    (let [no-move (rover/execute game "f")
          left-forward (rover/execute game "lf")
          l-f-r-f (rover/execute game "lfrf")
          l-f-r-f-f-r-f (rover/execute game "lfrffrf")]
      (is (= game no-move))
      (is (= (:position left-forward) [3 2]))
      (is (= (:position l-f-r-f) [2 2]))
      (is (= (:position l-f-r-f-f-r-f) [1 3])))))

(deftest test-new-pos
  (testing "test-new-position"
    (let [test-game {:world world :direction :south :position [4 4]}
          forward (rover/execute test-game "f")]
      (is (= (:position forward) [5 4])))))

(deftest test-move-over-northern-border
  (testing "What happens when you move too far north?"
    (let [test-game {:world world :direction :north :position [0 2]}
          after-move (rover/execute test-game "f")]
      (is (= (:position after-move) [5 2])))))

(deftest test-move-over-western-border
  (testing "What happens when you move too far west?"
    (let [test-game {:world world :direction :west :position [3 0]}
          after-move (rover/execute test-game "f")]
      (is (= (:position after-move) [3 5])))))

(deftest test-move-over-eastern-border
  (testing "What happens when you move too far east?"
    (let [test-game {:world world :direction :east :position [3 5]}
          after-move (rover/execute test-game "f")]
      (is (= (:position after-move) [3 0])))))

(deftest test-move-over-southern-border
  (testing "What happens when you move too far south?"
    (let [test-game {:world world :direction :south :position [5 4]}
          after-move (rover/execute test-game "f")]
      (is (= (:position after-move) [0 4])))))
