(ns ccron.parser-test
  (:require [ccron.parser :as parser]
            [clojure.test :refer :all]))

(def tests [
            { :title "normal line" :input "1 0 * * * ls" :expected { :min 1 :hour 0 :day-of-month nil :month nil :day-of-week nil :command "ls" }}
            { :title "normal line with space in command" :input "* 0 * * * ls -al" :expected { :min nil :hour 0 :day-of-month nil :month nil :day-of-week nil :command "ls -al" }}
            { :title "normal line with spaces" :input "* 0 * * *    ls   -al" :expected { :min nil :hour 0 :day-of-month nil :month nil :day-of-week nil :command "ls   -al" }}
            { :title "corrupted line" :input "* 0 * * ls -al" :expected nil }
            { :title "corrupted line with spaces in time specifier" :input "*   * 0 * * ls -al" :expected nil }
            { :title "wrong input" :input "wrong input" :expected nil }
            ])

(deftest can-parse-cron-line
  (testing "parsing line from the crontab file - "
    (doseq [test-expr tests]
      (testing (:title test-expr)
          (is (= (:expected test-expr) (parser/parse-line (:input test-expr))))))))
