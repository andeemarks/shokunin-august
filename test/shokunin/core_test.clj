(ns shokunin.core-test
  (:require [clojure.test :refer :all]
            [shokunin.core :as cut]))

(deftest generating-permutations-of-devs
  (testing "1 developer produces one result"
    (is (= [["a"]] (cut/permute ["a"]))))
  (testing "2 developers produces 2 results"
    (is (= [["a" "b"]["b" "a"]] (cut/permute ["a" "b"]))))
  (testing "3 developers produces 6 results"
    (is (= [["a" "b" "c"]["a" "c" "b"]
            ["b" "a" "c"]["b" "c" "a"]
            ["c" "a" "b"]["c" "b" "a"]] (cut/permute ["a" "b" "c"]))))
  (testing "4 developers produces 24 results"
    (is (= 24 (count (cut/permute ["a" "b" "c" "d"])))))
  (testing "permutations are not dependent on order"
    (is (cut/permute ["a" "b" "c" "d"]) (cut/permute ["d" "c" "b" "a"])))
  )