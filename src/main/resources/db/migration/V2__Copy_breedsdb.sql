copy public.breeds (_id, title, description, description_full, image_resource_id, image_resource_id_big,
obidience, guard, agressive, active, hardy, size, care, hunt, weblinc, weblinc_wiki, fciid,
hair, blackorwhite, noalergy, favorite, comment, for_child, for_company, for_running, for_hunt,
for_obidience, for_guardterritory, for_zks, for_agility) FROM '/home/repository/breeds.csv'
CSV HEADER ENCODING 'UTF8';