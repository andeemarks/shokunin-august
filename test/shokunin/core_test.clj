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

(deftest finding-possible-solutions
  (let [initial-solutions (cut/permute ["a" "b" "c" "d" "e"])]
    (testing "can find solutions with named devs at certain positions"
      (is (= 24 (count (cut/find initial-solutions "a" 1))))
      (is (= 24 (count (cut/find initial-solutions "a" 1 :inclusive)))))
    (testing "can find solutions with named devs not at certain positions"
      ; (is (= 96 (count (cut/find2 initial-solutions '(> 1 (pos "b"))))))
      )
    (testing "finds no solutions for unknown devs"
      (is (= 0 (count (cut/find initial-solutions "f" 1))))
      (is (= 0 (count (cut/find initial-solutions "A" 2))))
      (is (= 0 (count (cut/find initial-solutions "a " 3)))) 
      )
    (testing "finds no solutions for invalid positions"
      (is (= 0 (count (cut/find initial-solutions "a" 0))))
      (is (= 0 (count (cut/find initial-solutions "b" 6)))) )
  ))

(deftest finding-developer-positions
  (testing "can find position of existing developer"
    (is (= '({:pos 1} {:pos 2} {:pos 3}) (sort-by :pos (cut/pos "b" (cut/permute ["a" "b" "c"])))))
    (is (= '({:pos 1} {:pos 2}) (sort-by :pos (cut/pos "b" (cut/permute ["a" "b"]))))))
  (testing "cannot find position of non-existing developer"
    (is (= '() (cut/pos "c" (cut/permute ["a" "b"]))))))

; Jessie is not the best developer
; (> 1 (pos "Jessie"))
; Evan is not the worst developer
; (< 5 (pos "Evan"))
; John is not the best developer or the worst developer
; (and (> 1 (pos "John")) (< 5 (pos "John")))
; Sarah is a better developer than Evan
; (< (pos "Sarah") (pos "Evan"))
; Matt is not directly behind or ahead of John
; (> 1 (abs (- (pos "Matt") (pos "Evan"))))
; John is not directly behind or ahead of Evan
; (> 1 (abs (- (pos "John") (pos "Evan"))))
