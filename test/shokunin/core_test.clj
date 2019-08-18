(ns shokunin.core-test
  (:require [clojure.test :refer :all]
            [shokunin.core :as cut]))

(deftest generating-permutations-of-devs
  (testing "1 developer produces one result"
    (is (= [[{:dev "a" :pos 1}]] (cut/permute ["a"]))))
  (testing "2 developers produces 2 results"
    (is (= [[{:dev "a" :pos 1} {:dev "b" :pos 2}][{:dev "b" :pos 1}{:dev "a" :pos 2}]] (cut/permute ["a" "b"]))))
  (testing "4 developers produces 24 results"
    (is (= 24 (count (cut/permute ["a" "b" "c" "d"])))))
  (testing "permutations are not dependent on order"
    (is (cut/permute ["a" "b" "c" "d"]) (cut/permute ["d" "c" "b" "a"])))
  )
