select em.id as export_model_id, em.name as "export",
       fs.id as field_id, fs.column_index, fs.property_name as property, fs.header_label as label, fs.property_type as "field_type"
from export_model em,
     field_spec_model fs,
     export_model_field_spec emfs
where em.id = emfs.model_id
  and fs.id = emfs.field_spec_id
order by em.name, fs.column_index;