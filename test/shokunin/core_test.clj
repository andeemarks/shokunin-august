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

(deftest finding-possible-solutions
  (let [initial-solutions (cut/permute ["a" "b" "c" "d" "e"])]
    (testing "can find solutions with named devs at certain positions"
      (is (= 24 (count (cut/find initial-solutions "a" 1))))
      (is (= 24 (count (cut/find initial-solutions "a" 1 :inclusive)))))
    (testing "can find solutions with named devs not at certain positions"
      (is (= 96 (count (cut/find initial-solutions "b" 1 :exclusive)))))
    (testing "finds no solutions for unknown devs"
      (is (= 0 (count (cut/find initial-solutions "f" 1))))
      (is (= 0 (count (cut/find initial-solutions "A" 2))))
      (is (= 0 (count (cut/find initial-solutions "a " 3)))) 
      )
    (testing "finds no solutions for invalid positions"
      (is (= 0 (count (cut/find initial-solutions "a" 0))))
      (is (= 0 (count (cut/find initial-solutions "b" 6)))) )
  ))