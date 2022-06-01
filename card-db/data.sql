
-- Families
insert into public.family (id_family, name_family)
values (0, 'DC COMICS');

insert into public.family (id_family, name_family)
values (1, 'Marvel');

-- Cards
insert into public.card (id_card, affinity_card, description_card, energy_card, name_card, family_card_id_family, attack_card, hp_card, defence_card)
values 
(0, 'DC COMICS', 'Bruce Wayne, alias Batman, est un héros de fiction appartenant à l''univers de DC Comics', 80, 'Batman', 0, 170, 50, 80),
(1, 'MARVEL', 'C dedpool', 100, 'Deadpool', 1, 15, 999999, 15),
(2, 'DC COMICS', 'Description de superman [insérer un jeu de mot]', 100, 'Superman', 0, 50, 500, 50);

commit;