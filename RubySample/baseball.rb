# Declaration for Baseball
class Baseball
  def swing
    'Home run'
  end
end

p Baseball.new.swing

# Declaration for String
class String
  def censor(bad_word)
    self.gsub! "#{bad_word}", "CENSORED"
  end

  def num_of_chars
    size
  end
end

p "The bunny was in trouble with the king's bunny".censor('bunny')
p "The bunny was in trouble with the king's bunny".num_of_chars


