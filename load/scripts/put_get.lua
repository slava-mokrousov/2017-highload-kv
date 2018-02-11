counter = 0
method = 0

request = function()
   path = "/v0/entity?id=" .. counter
   if (method == 0) then
       wrk.method = "PUT"
       value = ""
       for i = 1, 4200 do
          value = value .. string.char(math.random(65, 122))
       end
       wrk.body = value
       method = 1
   else 
       wrk.method = "GET"
       counter = counter + 1
       method = 0
   end
   return wrk.format(nil, path)
end