select
#        em.id as export_model_id,
#        fs.id as field_id, fs.field_index,
em.name          as "export",
fs.property_name as property,
fs.label         as label,
ft.name          as field_type
from export_model em,
     field_spec_model fs,
     field_type_model ft,
     export_model_field_spec emfs
where em.id = emfs.model_id
  and fs.id = emfs.field_spec_id
  and fs.field_type_id = ft.code
order by em.name, fs.field_index;