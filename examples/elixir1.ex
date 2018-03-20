defmodule M do
  def main do
    # send(self(), {:english, "Ram"})

    receive do
      {:english, name} -> IO.puts("Hello, #{name}")
    after
      500 -> IO.puts("no msg")
    end

    Enum.map(1..10, fn num -> IO.write(num) end)
  end

  def spawn_test do
    spawn(fn -> loop(1, 100) end)
    spawn(fn -> loop(100, 200) end)
  end

  def do_stuff do
    my_str = "test"
    IO.puts("#{String.length(my_str)}")
    IO.puts(String.contains?(my_str, "es"))
    IO.puts(String.at(my_str, 3))

    inspect(String.split("asdf asdf sdf", ","))

    age = 16

    unless age === 18 do
      IO.puts("You are not adult")
    else
      IO.puts("You are adult")
    end

    cond do
      age >= 18 -> IO.puts("You can vote")
      age >= 16 -> IO.puts("You can drive")
      age >= 14 -> IO.puts("You can wait")
      true -> IO.puts("default")
    end

    case 1 do
      1 -> IO.puts("entered 1")
      _ -> IO.puts("default")
    end
  end

  def input do
    name = IO.gets("What is your name ?") |> String.trim()
    IO.puts("Hello #{name}")
  end

  def tuple_creator do
    my_stats = {175, 6.25, 30, :Ram}
    IO.puts(is_tuple(my_stats))
    IO.puts("Age is #{elem(my_stats, 2)}")
  end

  def list_creator do
    list1 = [1, 2, 3]
    list2 = [4, 5, 6]
    list3 = list1 ++ list2

    [head | tail] = list3
    IO.puts(head)
    IO.write("tail")
    IO.inspect(tail)

    Enum.each(tail, fn item ->
      IO.puts(item)
    end)

    display_list(tail)
    display_list(List.delete(tail, 3))
  end

  def loop(max, min) do
    cond do
      max < min ->
        loop(min, max)

      max == min ->
        nil

      true ->
        IO.puts("Num:#{max}")
        loop(max - 1, min)
    end
  end

  def display_list([item | items]) do
    IO.puts(item)
    display_list(items)
  end

  def display_list([]) do
    nil
  end
end
