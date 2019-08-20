(ns shokunin.august-test
  (:use [shokunin.august])
  (:use [shokunin.rankable])
  (:require [clojure.test :refer [deftest testing is]]))

(deftest rankable-devRankLists
  (testing "first? returns true if the given dev is first in the list"
    (is (true? (first? (->DevRankList ["a"]) "a")))
    (is (thrown? IllegalArgumentException (first? (->DevRankList ["a"]) "A")))
    (is (true? (first? (->DevRankList ["a" "b"]) "a")))
    (is (false? (first? (->DevRankList ["a" "b"]) "b")))
    (is (thrown? IllegalArgumentException (first? (->DevRankList ["a" "b"]) "c")))
    (is (thrown? IllegalArgumentException (first? (->DevRankList []) "c"))))

  (testing "last? returns true if the given dev is last in the list"
    (is (true? (last? (->DevRankList ["a"]) "a")))
    (is (thrown? IllegalArgumentException (last? (->DevRankList ["a"]) "A")))
    (is (true? (last? (->DevRankList ["a" "b"]) "b")))
    (is (false? (last? (->DevRankList ["a" "b"]) "a")))
    (is (thrown? IllegalArgumentException (last? (->DevRankList ["a" "b"]) "c"))))

  (testing "rank-for errors if the given dev is not in the list"
    (is (thrown? IllegalArgumentException (rank-for (->DevRankList ["a"]) "A"))))

  (testing "rank-for returns the rank for the given dev in the list"
    (is (= 1 (rank-for (->DevRankList ["a"]) "a")))
    (is (= 2 (rank-for (->DevRankList ["a" "b"]) "b")))
    (is (= 1 (rank-for (->DevRankList ["a" "b"]) "a"))))

  (testing "better-than? returns true if the first given dev has a higher rank than the second given dev"
    (is (false? (better-than? (->DevRankList ["a"]) "a" "a")))
    (is (true? (better-than? (->DevRankList ["a" "b"]) "a" "b")))
    (is (false? (better-than? (->DevRankList ["a" "b"]) "b" "a"))))

  (testing "neighbours? returns true if the first given dev has a rank directly below or above the second given dev"
    (is (false? (neighbours? (->DevRankList ["a"]) "a" "a")))
    (is (true? (neighbours? (->DevRankList ["a" "b"]) "a" "b")))
    (is (true? (neighbours? (->DevRankList ["a" "b"]) "b" "a")))
    (is (false? (better-than? (->DevRankList ["a" "b" "c"]) "c" "a"))))

  (testing "gap-between returns the difference in ranks between the two given devs"
    (is (= 0 (gap-between (->DevRankList ["a"]) "a" "a")))
    (is (= 3 (gap-between (->DevRankList ["a" "b" "c" "d"]) "a" "d")))
    (is (= 3 (gap-between (->DevRankList ["a" "b" "c" "d"]) "d" "a")))))